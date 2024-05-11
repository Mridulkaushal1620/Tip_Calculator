package com.example.tipcalculator2

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Switch
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import java.text.NumberFormat
import com.example.tipcalculator2.ui.theme.Tipcalculator2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tipcalculator2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var amountInput by remember{ mutableStateOf("")}
    var tipInput by remember {
        mutableStateOf("")
    }
    var tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    var amount = amountInput.toDoubleOrNull() ?: 0.0
    var roundUp by remember {
        mutableStateOf(false)
    }
    val tip = calculateTip(amount,tipPercent,roundUp)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(150.dp))
        EditNumberFeild(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            value = amountInput,
            onValueChange = {amountInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )

        )
        EditNumberFeild(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            value = tipInput,
            onValueChange = {tipInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ))
        RoundTheTipRow(
            modifier = Modifier.padding(32.dp),
            roundUp = roundUp,
            onRoundUpChanged = {roundUp = it}

        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency.
 * Example would be "$10.00".
 */                                                                                                                                     
@VisibleForTesting
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if(roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun RoundTheTipRow(
    modifier: Modifier = Modifier,
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit
){
    Row(modifier = modifier
        .fillMaxWidth()
        .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.round_up_tip),
            textAlign = TextAlign.Start)
        Switch(modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
        )
    }
}
@Composable
fun EditNumberFeild(
    modifier:Modifier = Modifier,
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions

) {


    Column {
        TextField(
            value = value,
            leadingIcon = {Icon(painterResource(id = leadingIcon),null)},
            onValueChange = onValueChange,
            modifier = modifier,
            label = {Text( stringResource(label)) },
            singleLine = true,
            keyboardOptions = keyboardOptions


        )

    }
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    Tipcalculator2Theme {
        TipTimeLayout()
    }
}