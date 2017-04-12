package hr.ferit.kstefancic.dailytasker;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewTaskActivity extends Activity implements View.OnClickListener {

    private static final String ERROR_EMPTY_BOX ="Please enter the title and choose a category or add a new one" ;
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
        setSpinner();
    }

    private void setSpinner() {
        ArrayList<String> categories = TaskDBHelper.getInstance(this).getCategories();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(spinnerAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
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

                Task task = getTaskAttr();
                TaskDBHelper.getInstance(getApplicationContext()).insertTask(task);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                this.startActivity(intent);
        }
    }

    private Task getTaskAttr() {
        String title = this.etTitle.getText().toString();
        String description = this.etDescription.getText().toString();
        String category;
        if (etCategory.getVisibility() == View.VISIBLE) {
            category = this.etCategory.getText().toString();
        } else {
            category = String.valueOf(this.spinnerCategories.getSelectedItem());
        }
        int priorityColor = getPriorityColor();
        Date now = new Date();
        String stringDate = new SimpleDateFormat("dd. MMMM, yyyy.").format(now);
        Task task = new Task(title, description, category, stringDate, priorityColor);
        return task;
    }

    private int getPriorityColor() {
        this.rbgPriorities = (RadioGroup) findViewById(R.id.rgPriorities);
        int rbId = rbgPriorities.getCheckedRadioButtonId();
        this.rbPriority= (RadioButton) findViewById(rbId);
        switch (rbId){
            case R.id.rbPriority1:
                return R.color.colorRed;

            case R.id.rbPriority2:
                return R.color.colorYellow;

            case R.id.rbPriority3:
                return R.color.colorGreen;
        }
        return 0;
    }
}
