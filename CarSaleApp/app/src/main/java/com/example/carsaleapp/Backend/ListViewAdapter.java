package com.example.carsaleapp.Backend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.carsaleapp.Backend.Tree.RBTree;
import com.example.carsaleapp.Backend.Tree.RBTreeSingleton;
import com.example.carsaleapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//  @author: Preetish Thirumalai u7157098
public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Car> carsList = null;
    private final ArrayList<Car> arraylist;

    public ListViewAdapter(Context context, List<Car> carsList) {
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

    // Filter Class
    public void filter(String charText) {
        //charText = charText.toLowerCase(Locale.getDefault());
        carsList.clear();
        boolean isString;
        try {
            Integer.parseInt(charText);
            isString= false;
        } catch (NumberFormatException e) {
            isString= true;
        }
        if(isString){
            List temp1=(RBTreeSingleton.getMakeInstance().search(charText));
            if(temp1!=null){
                carsList.addAll(temp1);
            }
        }
        else{
            List temp2=RBTreeSingleton.getPriceInstance().getAll(Integer.parseInt(charText),Integer.parseInt(charText));
            if(temp2!=null){
                carsList.addAll(temp2);
            }

        }
        if(carsList!=null){
            notifyDataSetChanged();
        }
//        if (charText.length() == 0) {
//            carsList.addAll(arraylist);
//        }
//        if(charText.equals("$")) {
//            carsList.addAll(arraylist);
//        }
//        else {
//            for (Car wp : arraylist) {
//                if (wp.getMake().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    carsList.add(wp);
//                }
//                int c;
//                try {
//                    c = Integer.parseInt(charText);
//                } catch (NumberFormatException e) {
//                    continue;
//                }
//                if (wp.getPrice() == c) {
//                    carsList.add(wp);
//                }
//            }
//        }

    }
}

