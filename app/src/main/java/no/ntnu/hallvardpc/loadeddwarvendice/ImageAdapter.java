package no.ntnu.hallvardpc.loadeddwarvendice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Alexander Eilert Berg on 29/11/2017.
 */

class ImageAdapter extends BaseAdapter {
    private Context context;
    private int numberOfDice = 0;

    private List<Integer> diceIDs;


    public ImageAdapter(Context context, int numberOfDice, List<Integer> diceIDs) {
        this.context = context;
        this.numberOfDice = numberOfDice;
        this.diceIDs = diceIDs;
    }

    @Override
    public int getCount() {
        return numberOfDice;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView diceImage = new ImageView(context);
        diceImage.setPadding(10, 30, 10, 30);

        diceImage.setAdjustViewBounds(true);
        diceImage.setMaxHeight(256);
        diceImage.setMaxWidth(256);
        diceImage.setScaleType(ImageView.ScaleType.FIT_CENTER);

        try {
            diceImage.setImageResource(diceIDs.get(i));
        } catch (IndexOutOfBoundsException e) {
        }
        return diceImage;
    }

}
