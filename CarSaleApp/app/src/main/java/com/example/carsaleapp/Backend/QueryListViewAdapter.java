package com.example.carsaleapp.Backend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carsaleapp.Backend.Grammar.IntExp;
import com.example.carsaleapp.Backend.Grammar.Parser;
import com.example.carsaleapp.Backend.Grammar.Tokenizer;
import com.example.carsaleapp.Backend.Tree.RBTreeSingleton;
import com.example.carsaleapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//  @author: Preetish Thirumalai u7157098
// Template design pattern, this class uses 'ListViewAdapter as template
public class QueryListViewAdapter extends ListViewAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Car> carsList = null;
    private final ArrayList<Car> arraylist;

    public QueryListViewAdapter(Context context, List<Car> carsList) {
        super(context, carsList);
        mContext = context;
        this.carsList = carsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Car>();
        this.arraylist.addAll(carsList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return carsList.size();
    }

    @Override
    public Car getItem(int position) {
        return carsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //set the View
    @SuppressLint("SetTextI18n")
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(" " +carsList.get(position).getYear() + " "
                + carsList.get(position).getMake() + " " + carsList.get(position).getModel() +
                " - "+carsList.get(position).getPrice());

        return view;
    }

    // Filter Class, filter based on price
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        carsList.clear();
        if (charText.length() == 0) {
            carsList.addAll(arraylist);
        }
        if(charText.equals("$")) {
            carsList.addAll(arraylist);
        }
        else {
            //parse the input, determine if it is less than or greater than
            Tokenizer mathTokenizer = new Tokenizer(charText);
            IntExp t1 = new Parser(mathTokenizer).parseExp();
            if (t1.lessOrGreater()) {
                List temp1=RBTreeSingleton.getPriceInstance().getAll(0, t1.getValue());
                if(temp1!=null) carsList.addAll(temp1);
            }
            else {
                List temp2=RBTreeSingleton.getPriceInstance().getAll(t1.getValue(),Integer.MAX_VALUE);
                if(temp2!=null) carsList.addAll(temp2);
            }
//            for (Car car : arraylist) {
//                if (t1.lessOrGreater()) {
//                    if (car.getPrice() < t1.getValue()) {
//                        carsList.add();
//                    }
//                }
//                else {
//                    if (car.getPrice() > t1.getValue()) {
//                        carsList.add(car);
//                    }
//                }
//            }

        }
        notifyDataSetChanged();
    }
}
