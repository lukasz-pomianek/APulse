package com.apulse.ui.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a2\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u001e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\u0012\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\tH\u0002\u00a8\u0006\u0014"}, d2 = {"AddRedactionRuleDialog", "", "onRuleAdded", "Lkotlin/Function1;", "Lcom/apulse/redaction/RedactionRule;", "onDismiss", "Lkotlin/Function0;", "ComplianceModeDialog", "currentMode", "Lcom/apulse/redaction/ComplianceMode;", "onModeSelected", "RedactionRuleItem", "rule", "onDelete", "SecuritySettingsScreen", "viewModel", "Lcom/apulse/ui/settings/SecuritySettingsViewModel;", "getComplianceModeDescription", "", "mode", "app_release"})
public final class SecuritySettingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void SecuritySettingsScreen(@org.jetbrains.annotations.NotNull
    com.apulse.ui.settings.SecuritySettingsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void RedactionRuleItem(com.apulse.redaction.RedactionRule rule, kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ComplianceModeDialog(com.apulse.redaction.ComplianceMode currentMode, kotlin.jvm.functions.Function1<? super com.apulse.redaction.ComplianceMode, kotlin.Unit> onModeSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void AddRedactionRuleDialog(kotlin.jvm.functions.Function1<? super com.apulse.redaction.RedactionRule, kotlin.Unit> onRuleAdded, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    private static final java.lang.String getComplianceModeDescription(com.apulse.redaction.ComplianceMode mode) {
        return null;
    }
}