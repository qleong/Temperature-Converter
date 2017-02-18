package com.coderschool.temperatureconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Activity class that converts fahrenheit to celsius and vice versa.
 *
 * Created By: Quintin Leong
 * Date: Feb 17, 2017
 * Time: 9:00:00 PM
 */
public class MainActivity extends AppCompatActivity {

    private EditText mInputEditText;
    private TextView mDisplayTextView;
    private ToggleButton mToggleButton;

    private static final String FAHRENHEIT = "fahrenheit";
    private static final String CELSIUS = "celsius";

    /**
     * {@inheritDoc}
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIElements();
    }

    /**
     * Function to attach UI elements to member variables.
     *
     */
    private void setupUIElements() {
        mInputEditText = (EditText) findViewById(R.id.editText1);
        mDisplayTextView = (TextView) findViewById(R.id.textView);
        mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        attachEditViewInputListeners();
        attachToggleButtonlisteners();
    }

    /**
     * Attaches input <code>EditView</code> listeners.
     *
     */
    private void attachEditViewInputListeners() {
        mInputEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(final Editable s) {
                updateOutputDisplay();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    /**
     * Attaches behavior of <code>ToggleButton</code>.
     *
     */
    private void attachToggleButtonlisteners() {
        // Set toggle button label values when on/off.
        mToggleButton.setText("Celsius -> Fahrenheit");
        mToggleButton.setTextOff("Fahrenheit -> Celsius");
        mToggleButton.setTextOn("Celsius -> Fahrenheit");

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateOutputDisplay();
            }
        });
    }

    /**
     * Updates the output display with the conversion of the input.
     *
     */
    private void updateOutputDisplay() {
        final String inputText = mInputEditText.getText().toString();
        if (inputText.length() == 0) {
            mInputEditText.setHint("0 degrees ".concat(
                mToggleButton.isChecked() ? CELSIUS : FAHRENHEIT));
            mDisplayTextView.setText(formatOutput(calculateTemperatureConversion(0.0)));
            return;
        }
        try {
            final Double inputTemp = calculateTemperatureConversion(
                    Double.parseDouble(inputText));
            mDisplayTextView.setText(formatOutput(inputTemp));
        } catch (final NumberFormatException e) {
            mDisplayTextView.setText("");
        }
    }

    /**
     * Calculates the conversion of temperature.
     * Makes the correct calculation based on state of <code>mToggleButton</code>.
     *
     * @param inputTemp
     *   Input from <code>mInputEditText</code>.
     * @return
     *   <code>Double</code> value of the converted temperature.
     */
    private Double calculateTemperatureConversion(final Double inputTemp) {
        return mToggleButton.isChecked() ? (inputTemp * 9 / 5) + 32 : (inputTemp - 32) * 5 / 9;
    }

    /**
     * Creates formatted output of output temperature that can be placed in the display.
     *
     * @param outputTemp the temperature to output to <code>mDisplayTextView</code>.
     *
     * @return
     *   Formatted <code>String</code> with correct temperature unit.
     *
     */
    private String formatOutput(final Double outputTemp) {
        // Turn into ternary based on toggle.
        return String.format(
            "%.1f degrees ".concat(mToggleButton.isChecked() ? FAHRENHEIT : CELSIUS),
            outputTemp);
    }

}
