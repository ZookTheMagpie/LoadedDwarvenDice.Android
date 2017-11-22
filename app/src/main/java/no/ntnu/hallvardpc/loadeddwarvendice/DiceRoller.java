package berg.dwarvendiceroller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner diceTypeSpinner = (Spinner) findViewById(R.id.spinnerDiceType);
    RadioButton minus = (RadioButton) findViewById(R.id.radioButtonMinus);
    RadioButton plus = (RadioButton) findViewById(R.id.radioButtonPlus);
    NumberPicker modifier = (NumberPicker) findViewById(R.id.modifier);
    NumberPicker numberOfDice = (NumberPicker) findViewById(R.id.numberOfDice);
    Button rollDice = (Button) findViewById(R.id.roll);
    TextView diceResult = (TextView) findViewById(R.id.result);

    Integer diceTypeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dice type selection
        ArrayAdapter<CharSequence> diceAdapter = ArrayAdapter.createFromResource(this, R.array.diceTypeOptions, android.R.layout.simple_spinner_item);
        diceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceTypeSpinner.setAdapter(diceAdapter);
        diceTypeSpinner.setOnItemSelectedListener(this);

        //Setting modifier range, current range 0-100
        modifier.setMinValue(0);
        modifier.setMaxValue(100);

        //Setting number of dice range, current range 1-100
        numberOfDice.setMinValue(1);
        numberOfDice.setMaxValue(100);


        //Rolling the dice
        rollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diceResult();
            }
        });


    }

    /**
     * Adds up dice throw and modifiers
     * Checks what type of modifier is selected (+ or -)
     * Checks if all necessary information is given (I.E dice type), if not provided sends an error message
     */
    private void diceResult() {
        int modifierValue = modifier.getValue();
        int numberOfDiceValue = numberOfDice.getValue();
        Integer diceTypeValue = (Integer) diceTypeSpinner.getSelectedItem();


        //Check if dice type is selected, if not send error message
        if (diceTypeValue == null) {
            AlertDialog.Builder emptyTypeSelectionAlert = new AlertDialog.Builder(this);

            emptyTypeSelectionAlert.setMessage("Please select a dice type");
            emptyTypeSelectionAlert.setTitle("Missing selection");
            emptyTypeSelectionAlert.setPositiveButton("OK", null);
            emptyTypeSelectionAlert.setCancelable(true);
            emptyTypeSelectionAlert.create().show();

            emptyTypeSelectionAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        } else {
            int currentResult = 0;
            for (int i = 0; i <= numberOfDiceValue; i++)
            {
                if (plus.isChecked())
                {
                    currentResult += rollDice(diceTypeValue) + modifierValue;
                } else if (minus.isChecked())
                {
                    int roll = rollDice(diceTypeValue) - modifierValue;
                    if (roll <= 0)
                    {
                        currentResult += 0;
                    }else
                    {
                        currentResult += roll;
                    }
                }
            }
            diceResult.setText(String.valueOf(currentResult));
        }
    }

    /**
     * Rolls a single dice of a specific type
     * @return The dice result
     */
    private Integer rollDice(int dice)
    {
        int result = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            result = ThreadLocalRandom.current().nextInt(1, dice + 1);
        }
        return result;
    }


    /**
     * Selects the dice type
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        diceTypeValue = (Integer) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
