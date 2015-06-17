package its.farkel;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainFarkel extends ActionBarActivity {

    private static Dice hand = new Dice();
    private static int[] drawables = {0, R.drawable.die1, R.drawable.die2, R.drawable.die3, R.drawable.die4, R.drawable.die5, R.drawable.die6};
    private static int[] ids = {0, R.id.keep1, R.id.keep2, R.id.keep3, R.id.keep4, R.id.keep5, R.id.keep6};
    private static boolean[] available = {false, true, true, true, true, true, true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_farkel);
    }

    private void showDie(int num) {
        for (int i = 1; i <= 6; i++) {
            if (available[i]) {
                ImageView v = (ImageView)findViewById(ids[i]);
                v.setImageResource(drawables[num]);
                v.setVisibility(ImageView.VISIBLE);
                available[i] = false;
            }
        }
    }

    private void redisplay() {
        int i;
        for(i = 1; i <= hand.held; i++) {
            ImageView v = (ImageView)findViewById(ids[i]);
            v.setImageResource(drawables[hand.die[i-1]]);
            v.setVisibility(ImageView.VISIBLE);
            available[i] = false;
        }
        for(  ; i <= 6; i++) {
            ImageView v = (ImageView)findViewById(ids[i]);
            v.setVisibility(ImageView.INVISIBLE);
            available[i] = true;
        }
    }

    public void onclick1(ImageView view) {
        int num = 1;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick2(ImageView view) {
        int num = 2;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick3(ImageView view) {
        int num = 3;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick4(ImageView view) {
        int num = 4;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick5(ImageView view) {
        int num = 5;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclick6(ImageView view) {
        int num = 6;
        if (hand.addDie(num)) {
            showDie(num);
        }
    }

    public void onclickSolve(View view) {
        if (hand.held != 6) {
            return;
        }
        hand = hand.bestHand();
        redisplay();
    }

    public void onclickKeep1(ImageView view) {
        int num = 1;
        hand.removeDie(num);
        view.setVisibility(ImageView.INVISIBLE);
        available[num] = true;
    }

    public void onclickKeep2(ImageView view) {
        int num = 2;
        hand.removeDie(num);
        view.setVisibility(ImageView.INVISIBLE);
        available[num] = true;
    }

    public void onclickKeep3(ImageView view) {
        int num = 3;
        hand.removeDie(num);
        view.setVisibility(ImageView.INVISIBLE);
        available[num] = true;
    }

    public void onclickKeep4(ImageView view) {
        int num = 4;
        hand.removeDie(num);
        view.setVisibility(ImageView.INVISIBLE);
        available[num] = true;
    }

    public void onclickKeep5(ImageView view) {
        int num = 5;
        hand.removeDie(num);
        view.setVisibility(ImageView.INVISIBLE);
        available[num] = true;
    }

    public void onclickKeep6(ImageView view) {
        int num = 6;
        hand.removeDie(num);
        view.setVisibility(ImageView.INVISIBLE);
        available[num] = true;
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