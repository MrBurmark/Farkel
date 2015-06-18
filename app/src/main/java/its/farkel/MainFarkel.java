package its.farkel;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFarkel extends ActionBarActivity {

    private static Dice hand = new Dice();
    private static int[] drawables = {0, R.drawable.die1, R.drawable.die2, R.drawable.die3, R.drawable.die4, R.drawable.die5, R.drawable.die6};
    private static int[] ids = {0, R.id.keep1, R.id.keep2, R.id.keep3, R.id.keep4, R.id.keep5, R.id.keep6};
    private static boolean[] available = {false, true, true, true, true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_farkel);
        FarkelSolver.initFarkel();
    }

    private void showDie(int num) {
        for (int i = 1; i <= 6; i++) {
            if (available[i]) {
                ImageView v = (ImageView)findViewById(ids[i]);
                v.setImageResource(drawables[num]);
                v.setVisibility(ImageView.VISIBLE);
                v.setImageAlpha(255 / 2);
                available[i] = false;
                revalue();
                return;
            }
        }
    }

    private void removeDie(int id) {
        if (!available[id]){
            int val = hand.die[id-1];
            hand.removeDie(val);
            ImageView v = (ImageView)findViewById(ids[id]);
            v.setVisibility(ImageView.INVISIBLE);
            available[id] = true;
            revalue();
        }
    }

    private void redisplay() {
        int i;
        for(i = 1; i <= hand.held; i++) {
            ImageView v = (ImageView)findViewById(ids[i]);
            v.setImageResource(drawables[hand.die[i - 1]]);
            v.setVisibility(ImageView.VISIBLE);
            v.setImageAlpha(255);
            available[i] = false;
        }
        for(  ; i <= 6; i++) {
            if (hand.die[i-1] != 0) {
                ImageView v = (ImageView)findViewById(ids[i]);
                v.setImageResource(drawables[hand.die[i - 1]]);
                v.setVisibility(ImageView.VISIBLE);
                v.setImageAlpha(255 / 2);
                available[i] = false;
            } else {
                ImageView v = (ImageView)findViewById(ids[i]);
                v.setVisibility(ImageView.INVISIBLE);
                available[i] = true;
            }
        }
        revalue();
    }

    private void revalue() {
        TextView v = (TextView)findViewById(R.id.KeeptextView);
        v.setText(String.format("Value - %d\nExpected - %.1f", hand.value(), hand.expectedValue()));
    }

    public void onclick1(View view) {
        int num = 1;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick2(View view) {
        int num = 2;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick3(View view) {
        int num = 3;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick4(View view) {
        int num = 4;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick5(View view) {
        int num = 5;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick6(View view) {
        int num = 6;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclickSolve(View view){
        Dice newHand = hand.bestHand();
        if(newHand.expectedValue() > hand.value() && newHand.held > hand.held) {
            // roll again
            hand.copy(newHand);
        } else {
            // stay
            hand.empty();
        }
        redisplay();
    }

    public void onclickReset(View view) {
        hand.empty();
        redisplay();
    }

    public void onclickKeep1(View view) {
        int id = 1;
        removeDie(id);
    }

    public void onclickKeep2(View view) {
        int id = 2;
        removeDie(id);
    }

    public void onclickKeep3(View view) {
        int id = 3;
        removeDie(id);
    }

    public void onclickKeep4(View view) {
        int id = 4;
        removeDie(id);
    }

    public void onclickKeep5(View view) {
        int id = 5;
        removeDie(id);
    }

    public void onclickKeep6(View view) {
        int id = 6;
        removeDie(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_farkel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
