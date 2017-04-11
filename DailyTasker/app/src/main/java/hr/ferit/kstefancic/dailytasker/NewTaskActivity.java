package hr.ferit.kstefancic.dailytasker;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.Serializable;

public class NewTaskActivity extends Activity implements View.OnClickListener {

    Button btnNewCategory, btnAddTask, btnChooseCategory;
    EditText etTitle, etDescription, etCategory;
    Spinner spinnerCategories;
    RadioGroup rbgPriorities;
    RadioButton rbPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        setUI();

        this.btnAddTask.setOnClickListener(this);
        this.btnNewCategory.setOnClickListener(this);
        this.btnChooseCategory.setOnClickListener(this);
    }

    private void setUI() {
        this.btnAddTask = (Button) findViewById(R.id.btnAddTask);
        this.btnNewCategory= (Button) findViewById(R.id.btnNewCategory);
        this.etCategory= (EditText) findViewById(R.id.etCategory);
        this.etDescription= (EditText) findViewById(R.id.etDesc);
        this.etTitle= (EditText) findViewById(R.id.etTitle);
        this.btnChooseCategory= (Button) findViewById(R.id.btnChooseCategory);
        this.spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnNewCategory:
                this.etCategory.setVisibility(View.VISIBLE);
                this.spinnerCategories.setVisibility(View.GONE);
                this.btnNewCategory.setVisibility(View.GONE);
                this.btnChooseCategory.setVisibility(View.VISIBLE);
                break;

            case R.id.btnChooseCategory:
                this.etCategory.setVisibility(View.GONE);
                this.spinnerCategories.setVisibility(View.VISIBLE);
                this.btnNewCategory.setVisibility(View.VISIBLE);
                this.btnChooseCategory.setVisibility(View.GONE);
                break;

            case R.id.btnAddTask:
                String title=this.etTitle.getText().toString();
                String description = this.etDescription.getText().toString();
                String category;
                if(etCategory.getVisibility()==View.VISIBLE){
                    category = this.etCategory.getText().toString();
                }
                else {
                    category = String.valueOf(this.spinnerCategories.getSelectedItem());
                }
                int priority = rbgPriorities.getCheckedRadioButtonId()+1;
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
                Task task = new Task(title,description,category,String.valueOf(df.format(c.getTime())),priority);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.KEY_TASK, (Serializable) task);
                this.setResult(RESULT_OK,resultIntent);
                this.finish();
        }
    }
}
