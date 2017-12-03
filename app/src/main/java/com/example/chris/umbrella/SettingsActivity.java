package com.example.chris.umbrella;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.umbrella.exceptions.ZipcodeNotRecognizedException;

public class SettingsActivity extends AppCompatActivity
{
    
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean validZip;
    private String zip;
    private TextView tvUnits;
    private TextView tvZip;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        validZip = false;
    
        tvZip = findViewById(R.id.tvZip);
        tvUnits = findViewById(R.id.tvUnits);
        
        tvZip.setText(sharedPreferences.getString("zip", ""));
        tvUnits.setText(sharedPreferences.getString("units", "Fahrenheit"));
    }
    
    public void requestZip(final View view)
    {
        if (!validZip)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(this);
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setMessage("Enter Zipcode");
            alert.setTitle("Enter Zipcode");
        
            alert.setView(edittext);
            alert.setPositiveButton("Accept", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    zip = edittext.getText().toString();
                    try
                    {
                        //check to make sure zip is 5 digits
                        if (zip.length() != 5)
                            throw new ZipcodeNotRecognizedException();
                        //check to make sure zip is only numbers
                        Integer.parseInt(zip);
                        editor.putString("zip", zip);
                        boolean isSaved = editor.commit();
                        if (isSaved)
                        {
                            validZip = true;
                            Toast.makeText(SettingsActivity.this, "Saved zipcode", Toast.LENGTH_SHORT).show();
                            requestZip(view);
                        }
                    }
                    catch (ZipcodeNotRecognizedException e)
                    {
                        Toast.makeText(SettingsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        requestZip(view);
                    }
                    catch (NumberFormatException e)
                    {
                        Toast.makeText(SettingsActivity.this, "Zipcode must only contain numbers.", Toast.LENGTH_SHORT).show();
                        requestZip(view);
                    }
                }
            });
        
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    Toast.makeText(SettingsActivity.this, "Valid zipcode must be entered.", Toast.LENGTH_SHORT).show();
                    requestZip(view);
                }
            });
        
            alert.show();
        }
    }
    
    public void requestUnits(final View view)
    {
    
    }
}
