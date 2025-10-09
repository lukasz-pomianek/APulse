package com.apulse.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.apulse.redaction.ComplianceMode
import com.apulse.redaction.RedactionCategory
import com.apulse.redaction.RedactionRule
import com.apulse.redaction.RiskLevel
import com.apulse.ui.APulseViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecuritySettingsScreen(
    viewModel: SecuritySettingsViewModel = viewModel(factory = APulseViewModelFactory(LocalContext.current))
) {
    val complianceMode by viewModel.complianceMode.collectAsState()
    val enabledCategories by viewModel.enabledRedactionCategories.collectAsState()
    val customRules by viewModel.customRedactionRules.collectAsState()
    val securityRisks by viewModel.securityRisks.collectAsState()
    val encryptionInfo by viewModel.encryptionInfo.collectAsState()
    
    var showAddRuleDialog by remember { mutableStateOf(false) }
    var showComplianceDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Compliance Mode Section
        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Compliance Mode",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedButton(onClick = { showComplianceDialog = true }) {
                            Text(complianceMode.displayName)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = getComplianceModeDescription(complianceMode),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Security Risks Section
        if (securityRisks.isNotEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Security Risks (${securityRisks.size})",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        securityRisks.forEach { risk ->
                            Row(
                                verticalAlignment = Alignment.Top,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    when (risk.level) {
                                        RiskLevel.HIGH, RiskLevel.CRITICAL -> Icons.Default.Error
                                        RiskLevel.MEDIUM -> Icons.Default.Warning
                                        RiskLevel.LOW -> Icons.Default.Info
                                    },
                                    contentDescription = null,
                                    tint = when (risk.level) {
                                        RiskLevel.HIGH, RiskLevel.CRITICAL -> MaterialTheme.colorScheme.error
                                        RiskLevel.MEDIUM -> MaterialTheme.colorScheme.tertiary
                                        RiskLevel.LOW -> MaterialTheme.colorScheme.primary
                                    },
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = risk.description,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Recommendation: ${risk.recommendation}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            if (risk != securityRisks.last()) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
        
        // Encryption Status
        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Data Encryption",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (encryptionInfo.isAvailable) "Available" else "Unavailable",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (encryptionInfo.isAvailable) 
                                    MaterialTheme.colorScheme.primary
                                else 
                                    MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = "${encryptionInfo.algorithm} (${encryptionInfo.keyLocation})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (encryptionInfo.hardwareBacked) {
                            Surface(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "Hardware Backed",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Redaction Categories
        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Auto-Redaction Categories",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    RedactionCategory.values().forEach { category ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = category.displayName,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Switch(
                                checked = category in enabledCategories,
                                onCheckedChange = { enabled ->
                                    viewModel.toggleRedactionCategory(category, enabled)
                                }
                            )
                        }
                        
                        if (category != RedactionCategory.values().last()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
        
        // Custom Redaction Rules
        item {
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Rule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Custom Redaction Rules",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { showAddRuleDialog = true }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Rule")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (customRules.isEmpty()) {
                        Text(
                            text = "No custom rules configured",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        customRules.forEach { rule ->
                            RedactionRuleItem(
                                rule = rule,
                                onDelete = { viewModel.removeCustomRule(rule.name) }
                            )
                            if (rule != customRules.last()) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Dialogs
    if (showComplianceDialog) {
        ComplianceModeDialog(
            currentMode = complianceMode,
            onModeSelected = { mode ->
                viewModel.setComplianceMode(mode)
                showComplianceDialog = false
            },
            onDismiss = { showComplianceDialog = false }
        )
    }
    
    if (showAddRuleDialog) {
        AddRedactionRuleDialog(
            onRuleAdded = { rule ->
                viewModel.addCustomRule(rule)
                showAddRuleDialog = false
            },
            onDismiss = { showAddRuleDialog = false }
        )
    }
}

@Composable
private fun RedactionRuleItem(
    rule: RedactionRule,
    onDelete: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = rule.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = rule.pattern,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete Rule",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun ComplianceModeDialog(
    currentMode: ComplianceMode,
    onModeSelected: (ComplianceMode) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Compliance Mode") },
        text = {
            Column {
                ComplianceMode.values().forEach { mode ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = mode == currentMode,
                            onClick = { onModeSelected(mode) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = mode.displayName,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = getComplianceModeDescription(mode),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRedactionRuleDialog(
    onRuleAdded: (RedactionRule) -> Unit,
    onDismiss: () -> Unit
) {
    var ruleName by remember { mutableStateOf("") }
    var pattern by remember { mutableStateOf("") }
    var replacement by remember { mutableStateOf("[REDACTED]") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Custom Redaction Rule") },
        text = {
            Column {
                OutlinedTextField(
                    value = ruleName,
                    onValueChange = { ruleName = it },
                    label = { Text("Rule Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = pattern,
                    onValueChange = { pattern = it },
                    label = { Text("Regex Pattern") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = replacement,
                    onValueChange = { replacement = it },
                    label = { Text("Replacement Text") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (ruleName.isNotBlank() && pattern.isNotBlank()) {
                        val rule = RedactionRule(
                            name = ruleName,
                            pattern = pattern,
                            target = com.apulse.redaction.RedactionTarget.BODY_CONTENT,
                            replacement = replacement
                        )
                        onRuleAdded(rule)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun getComplianceModeDescription(mode: ComplianceMode): String {
    return when (mode) {
        ComplianceMode.PERMISSIVE -> "Minimal restrictions, allows most data capture"
        ComplianceMode.STANDARD -> "Balanced security with reasonable restrictions"
        ComplianceMode.STRICT -> "Maximum security with strict data handling"
        ComplianceMode.GDPR -> "GDPR compliant data handling and retention"
        ComplianceMode.HIPAA -> "HIPAA compliant for healthcare data"
    }
}