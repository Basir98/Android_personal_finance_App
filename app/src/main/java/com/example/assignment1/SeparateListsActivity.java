package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeparateListsActivity extends AppCompatActivity {

    ListView lw_income_list, lw_expense_list;

    Button btnBackToHome;
    DatabaseHelper DB;

    ArrayList<String> incomeArrayTitle;
    ArrayList<Integer> incomeArrayAmount;
    ArrayList<String> incomeArrayCategory;
    ArrayList<String> incomeArrayCalendar;

    ArrayList<String> expenseArrayTitle;
    ArrayList<Integer> expenseArrayAmount;
    ArrayList<String> expenseArrayCategory;
    ArrayList<String> expenseArrayCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separate_lists);

        lw_income_list = (ListView) findViewById(R.id.income_list);
        lw_expense_list = (ListView) findViewById(R.id.expense_list);

        btnBackToHome = (Button) findViewById(R.id.btn_back_to_home);

        //incomeList =new ArrayList<>();
        //expenseList =new ArrayList<>();

        incomeArrayTitle = new ArrayList<>();
        incomeArrayAmount= new ArrayList<>();
        incomeArrayCategory = new ArrayList<>();
        incomeArrayCalendar = new ArrayList<>();

        expenseArrayTitle= new ArrayList<>();
        expenseArrayAmount= new ArrayList<>();
        expenseArrayCategory= new ArrayList<>();
        expenseArrayCalendar= new ArrayList<>();

        DB = new DatabaseHelper(this);
        Cursor cursor = DB.getUserInfo(UserModel.username);
        if(cursor.getCount()==0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {

                incomeArrayTitle.clear();
                incomeArrayAmount.clear();
                incomeArrayCategory.clear();
                incomeArrayCalendar.clear();

                expenseArrayTitle.clear();
                expenseArrayAmount.clear();
                expenseArrayCategory.clear();
                expenseArrayCalendar.clear();

                Cursor cursorIncome = DB.viewSpecificTypeData(cursor.getString(0), "Income");
                Cursor cursorExpense = DB.viewSpecificTypeData(cursor.getString(0), "Expense");
                if(cursorIncome.getCount()== 0){
                    Toast.makeText(this, "No Data to Show in list", Toast.LENGTH_SHORT).show();
                }else {

                    while(cursorIncome.moveToNext()){

                        incomeArrayTitle.add(cursorIncome.getString(3));
                        incomeArrayAmount.add(cursorIncome.getInt(5));
                        incomeArrayCategory.add(cursorIncome.getString(4));
                        incomeArrayCalendar.add(cursorIncome.getString(6));
                    }
                    while(cursorExpense.moveToNext()){

                        expenseArrayTitle.add(cursorExpense.getString(3));
                        expenseArrayAmount.add(cursorExpense.getInt(5));
                        expenseArrayCategory.add(cursorExpense.getString(4));
                        expenseArrayCalendar.add(cursorExpense.getString(6));

                    }

                    }
            }
        }

        int[] incomeImages = new int[incomeArrayCategory.size()];

        for(int i=0; i< incomeArrayCategory.size(); i++){
            if(incomeArrayCategory.get(i).equals("Other")){
                incomeImages[i] = R.drawable.ic_other;
            }
            if(incomeArrayCategory.get(i).equals("Salary")){
                incomeImages[i] =  R.drawable.ic_salary;
            }
        }

        String[] strArrayTitle = GetStringArray(incomeArrayTitle);
        int[] intArrayAmount = GetIntArray(incomeArrayAmount);
        String[] strArrayCategory = GetStringArray(incomeArrayCategory);
        String[] strArrayCalendar = GetStringArray(incomeArrayCalendar);


        UserAdapter userAdapterIncome = new UserAdapter(incomeImages, strArrayTitle, intArrayAmount, strArrayCategory, strArrayCalendar);

        lw_income_list.setAdapter(userAdapterIncome);

        String[] strArrayTitleExp = GetStringArray(expenseArrayTitle);
        int[] intArrayAmountExp = GetIntArray(expenseArrayAmount);
        String [] strArrayCategoryExp = GetStringArray(expenseArrayCategory);
        String[] strArrayCalendarExp = GetStringArray(expenseArrayCalendar);


        int[] expenseImages = new int[expenseArrayCategory.size()];

        for(int i=0; i< expenseArrayCategory.size(); i++){
            if(expenseArrayCategory.get(i).equals("Other")){
                expenseImages[i] = R.drawable.ic_other;
            }
            if(expenseArrayCategory.get(i).equals("Food")){
                expenseImages[i] = R.drawable.ic_food;
            }
            if(expenseArrayCategory.get(i).equals("Accommodation")){
                expenseImages[i] = R.drawable.ic_accommodation;
            }
            if(expenseArrayCategory.get(i).equals("Travel")){
                expenseImages[i] = R.drawable.ic_travel;
            }
            if(expenseArrayCategory.get(i).equals("Leisure")){
                expenseImages[i] = R.drawable.ic_leisure;
            }
        }


        UserAdapter userAdapterExpense = new UserAdapter(expenseImages, strArrayTitleExp, intArrayAmountExp, strArrayCategoryExp, strArrayCalendarExp);

        lw_expense_list.setAdapter(userAdapterExpense);

        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


        /*
        lw_income_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SeparateListsActivity.this, "Item: "+adapter1.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

        lw_expense_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SeparateListsActivity.this, "Item: "+adapter2.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

         */

    }


    class UserAdapter extends BaseAdapter {

        int[] images;
        String[] title;
        int[] amount;
        String [] category;
        String[] calendar;

        public UserAdapter(int[] images, String[] title, int[] amount, String[] category, String[] calendar){
            this.images = images;
            this.title = title;
            this.amount = amount;
            this.category = category;
            this.calendar = calendar;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.list_view_layout, null);


            ImageView mImageView = (ImageView) view.findViewById(R.id.category_image_show);
            TextView mTextTitle = (TextView) view.findViewById(R.id.listView_titleText);
            TextView mTextAmount = (TextView) view.findViewById(R.id.listView_amountText);
            TextView mTextCategory = (TextView) view.findViewById(R.id.listView_category);
            TextView mTextCalendar = (TextView) view.findViewById(R.id.listView_calendar);


            mImageView.setImageResource(images[position]);
            mTextTitle.setText(title[position]);
            mTextAmount.setText(""+amount[position]);
            mTextCategory.setText(category[position]);
            mTextCalendar.setText(calendar[position]);

            return view;
        }
    }

    public static String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    public static int[] GetIntArray(ArrayList<Integer> arrayList){
        int[] arr = new int[arrayList.size()];
        int index = 0;
        for (final Integer value : arrayList) {
            arr[index++] = value;
        }
        return arr;
    }

}