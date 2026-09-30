package com.electron.catalog

import com.electron.catalog.demo.ConsumptionRange
import com.electron.catalog.demo.DemoIntent
import com.electron.catalog.demo.DemoState
import com.electron.catalog.demo.DemoTab
import com.electron.catalog.demo.reduce
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DemoReducerTest {

    @Test
    fun `selecting a tab or a range updates the state`() {
        val state = DemoState()
            .reduce(DemoIntent.SelectTab(DemoTab.Account.ordinal))
            .reduce(DemoIntent.SelectRange(ConsumptionRange.Month.ordinal))
        assertEquals(DemoTab.Account, state.tab)
        assertEquals(ConsumptionRange.Month, state.range)
    }

    @Test
    fun `out of range indexes are clamped`() {
        assertEquals(DemoTab.Account, DemoState().reduce(DemoIntent.SelectTab(42)).tab)
    }

    @Test
    fun `disconnecting asks first, then stops charging`() {
        val asked = DemoState().reduce(DemoIntent.RequestDisconnect)
        assertTrue(asked.isDisconnectDialogVisible)
        assertTrue(asked.isConnected)

        val dismissed = asked.reduce(DemoIntent.DismissDisconnect)
        assertFalse(dismissed.isDisconnectDialogVisible)
        assertTrue(dismissed.isConnected)

        val disconnected = asked.reduce(DemoIntent.ConfirmDisconnect)
        assertFalse(disconnected.isConnected)
        assertFalse(disconnected.isCharging)
        assertFalse(disconnected.isDisconnectDialogVisible)
    }

    @Test
    fun `charging can only be toggled while connected`() {
        val offline = DemoState().reduce(DemoIntent.RequestDisconnect).reduce(DemoIntent.ConfirmDisconnect)
        assertEquals(offline, offline.reduce(DemoIntent.ToggleCharging))
        assertFalse(DemoState().reduce(DemoIntent.ToggleCharging).isCharging)
    }

    @Test
    fun `the charge limit stays between 50 and 100 percent`() {
        assertEquals(0.5f, DemoState().reduce(DemoIntent.SetChargeLimit(0.1f)).chargeLimit)
        assertEquals(1f, DemoState().reduce(DemoIntent.SetChargeLimit(3f)).chargeLimit)
    }

    @Test
    fun `opening alerts marks them read`() {
        assertEquals(0, DemoState(pendingAlerts = 5).reduce(DemoIntent.OpenAlerts).pendingAlerts)
    }
}
