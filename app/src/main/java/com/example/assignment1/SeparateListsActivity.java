package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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

        lw_income_list = findViewById(R.id.income_list);
        lw_expense_list = findViewById(R.id.expense_list);
        btnBackToHome = findViewById(R.id.btn_back_to_home);

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

                        incomeArrayTitle.add(cursorIncome.getString(4));
                        incomeArrayAmount.add(cursorIncome.getInt(6));
                        incomeArrayCategory.add(cursorIncome.getString(5));
                        incomeArrayCalendar.add(cursorIncome.getString(7));
                    }
                    while(cursorExpense.moveToNext()){

                        expenseArrayTitle.add(cursorExpense.getString(4));
                        expenseArrayAmount.add(cursorExpense.getInt(6));
                        expenseArrayCategory.add(cursorExpense.getString(5));
                        expenseArrayCalendar.add(cursorExpense.getString(7));
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

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        });
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

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            @SuppressLint({"InflateParams", "ViewHolder"}) View view = getLayoutInflater().inflate(R.layout.list_view_layout, null);
            ImageView mImageView = view.findViewById(R.id.category_image_show);
            TextView mTextTitle = view.findViewById(R.id.listView_titleText);
            TextView mTextAmount = view.findViewById(R.id.listView_amountText);
            TextView mTextCategory = view.findViewById(R.id.listView_category);
            TextView mTextCalendar = view.findViewById(R.id.listView_calendar);

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
        String[] str = new String[arr.size()];

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