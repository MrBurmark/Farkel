package its.farkel;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFarkel extends ActionBarActivity {

    private enum die_states { NONE, UNHELD, ADVISED, HELD }
    private enum game_states { OK, SCRATCHED }

    private static Dice hand = new Dice();
    private static int[] drawables = {R.drawable.die0, R.drawable.die1, R.drawable.die2, R.drawable.die3, R.drawable.die4, R.drawable.die5, R.drawable.die6};
    private static int[] ids = {0, R.id.keep1, R.id.keep2, R.id.keep3, R.id.keep4, R.id.keep5, R.id.keep6};
    private static die_states[] die_status = {die_states.HELD, die_states.NONE, die_states.NONE, die_states.NONE, die_states.NONE, die_states.NONE, die_states.NONE};
    private static int points = 0;
    private static game_states game_state = game_states.OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_farkel);
        for(int i = 1; i <= 6; i++) {
            ImageView v = (ImageView)findViewById(ids[i]);
            v.setImageAlpha(255 / 4);
        }
        FarkelSolver.initFarkel();
    }

    private boolean adviseDie(int id) {
        if (die_status[id] == die_states.UNHELD) {
            int val = hand.die[id - 1];
            return hand.addAdvised(val);
        }
        return false;
    }

    private boolean unadviseDie(int id) {
        if (die_status[id] == die_states.ADVISED) {
            int val = hand.die[id - 1];
            return hand.removeAdvised(val);
        }
        return false;
    }

    private boolean removeDie(int id) {
        if (die_status[id] != die_states.NONE) {
            int val = hand.die[id - 1];
            return hand.removeDie(val);
        }
        return false;
    }

    private void redisplay() {
        int i;
        for(i = 1; i <= hand.held; i++) {
            ImageView v = (ImageView)findViewById(ids[i]);
            v.setImageResource(drawables[hand.die[i - 1]]);
            v.setImageAlpha(255);
            die_status[i] = die_states.HELD;
        }
        for(  ; i <= hand.advised; i++) {
            ImageView v = (ImageView)findViewById(ids[i]);
            v.setImageResource(drawables[hand.die[i - 1]]);
            v.setImageAlpha((int)(255.0f / 1.4f));
            die_status[i] = die_states.ADVISED;
        }
        for(  ; i <= 6; i++) {
            if (hand.die[i-1] != 0) {
                ImageView v = (ImageView)findViewById(ids[i]);
                v.setImageResource(drawables[hand.die[i - 1]]);
                v.setImageAlpha(255 / 3);
                die_status[i] = die_states.UNHELD;
            } else {
                ImageView v = (ImageView)findViewById(ids[i]);
                v.setImageResource(drawables[0]);
                v.setImageAlpha(255 / 4);
                die_status[i] = die_states.NONE;
            }
        }
        Button a = (Button)findViewById(R.id.AdviceButton);
        Button s = (Button) findViewById(R.id.StayButton);
        Button r = (Button) findViewById(R.id.RollButton);
        Button h = (Button)findViewById(R.id.HoldButton);

        a.setTextColor(Color.BLACK);
        s.setTextColor(Color.BLACK);
        r.setTextColor(Color.BLACK);

        a.setClickable(true);
        h.setClickable(true);

        revalue();
    }

    private void revalue() {
        TextView v = (TextView)findViewById(R.id.ValueTextView);
        if (game_state == game_states.OK) {
            v.setText(String.format("Value\n %.0f\nAdvised\n %.0f\nExpected\n %.1f", hand.value(), hand.advisedValue(), hand.advisedExpectedValue()));
        } else {
            v.setText("Value\n 0\nAdvised\n 0\nExpected\n 0.0");
        }
    }

    public void onclickRoll(View view){

        if (game_state == game_states.SCRATCHED) {
            hand.empty(); // previously scratched
            game_state = game_states.OK;
        } else if (hand.numDice() != 0 && hand.heldValue() >= hand.advisedValue()) {
            return; // invalid new holds
        }

        hand.roll(); // roll non-held/advised dice

        if (hand.value() <= hand.heldValue()) {
            // scratched
            game_state = game_states.SCRATCHED;
        }

        redisplay();

        if (game_state == game_states.SCRATCHED) {
            // show scratch by changing advise to red
            Button r = (Button)findViewById(R.id.AdviceButton);
            r.setTextColor(Color.argb(255, 215, 0, 0));
            r.setClickable(false);
            Button h = (Button)findViewById(R.id.HoldButton);
            h.setClickable(false);
        }
    }

    public void onclickAdvice(View view){
        if (game_state == game_states.SCRATCHED) return; // don't advise if scratched
        Button g, r;
        hand.advise();
        redisplay();
        if (hand.advisedExpectedValue() > hand.value() && (hand.advised > hand.held || hand.held == 0)) {
            // roll
            g = (Button)findViewById(R.id.RollButton);
            r = (Button)findViewById(R.id.StayButton);
        } else {
            // stay
            g = (Button)findViewById(R.id.StayButton);
            r = (Button)findViewById(R.id.RollButton);
        }
        g.setTextColor(Color.argb(255, 0, 175, 0));
        r.setTextColor(Color.argb(255, 215, 0, 0));
    }

    public void onclickStay(View view) {
        if (game_state != game_states.SCRATCHED) {
            points += (int) hand.value(); // add value
            TextView v = (TextView) findViewById(R.id.PlayerTextView);
            v.setText(String.format("Points\n%d", points));
        }

        game_state = game_states.OK;
        hand.empty();
        redisplay();
    }

    public void onclickReset(View view) {
        game_state = game_states.OK;
        points = 0;
        TextView v = (TextView)findViewById(R.id.PlayerTextView);
        v.setText(String.format("Points\n%d", points));
        hand.empty();
        redisplay();
    }

    public void onclickResetHand(View view) {
        game_state = game_states.OK;
        hand.empty();
        redisplay();
    }

    public void onclickHold(View view) {
        if (game_state == game_states.SCRATCHED) return; // don't let computer players use manual mode if scratched
        hand.held = hand.advised;
        hand.removeUnheld();
        redisplay();
    }

    private void dieAction(int id) {
        if (game_state == game_states.SCRATCHED) return; // don't add dice if scratched
        if (hand.addDie(id)) {
            redisplay();
        }
    }

    private void inHandAction(int id) {
        if (game_state == game_states.SCRATCHED) return; // don't change die states if scratched
        boolean redraw = false;
        if (die_status[id] == die_states.ADVISED) {
            redraw = unadviseDie(id);
        } else if (die_status[id] == die_states.UNHELD) {
            redraw = adviseDie(id);
        }
        if (redraw) {
            redisplay();
        }
    }

    public void onclick1(View view) {
        int id = 1;
        dieAction(id);
    }

    public void onclick2(View view) {
        int id = 2;
        dieAction(id);
    }

    public void onclick3(View view) {
        int id = 3;
        dieAction(id);
    }

    public void onclick4(View view) {
        int id = 4;
        dieAction(id);
    }

    public void onclick5(View view) {
        int id = 5;
        dieAction(id);
    }

    public void onclick6(View view) {
        int id = 6;
        dieAction(id);
    }

    public void onclickKeep1(View view) {
        int id = 1;
        inHandAction(id);
    }

    public void onclickKeep2(View view) {
        int id = 2;
        inHandAction(id);
    }

    public void onclickKeep3(View view) {
        int id = 3;
        inHandAction(id);
    }

    public void onclickKeep4(View view) {
        int id = 4;
        inHandAction(id);
    }

    public void onclickKeep5(View view) {
        int id = 5;
        inHandAction(id);
    }

    public void onclickKeep6(View view) {
        int id = 6;
        inHandAction(id);
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
