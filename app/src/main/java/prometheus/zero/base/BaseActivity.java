package prometheus.zero.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import prometheus.zero.R;

/**
 * Created by ishwor on 29/06/16.
 */
public class BaseActivity extends AppCompatActivity implements BaseContract.View {

    public Toolbar toolbar;
    /*Calling class*/
    String callingClass = getClass().getSimpleName();
    Class calling = getClass();

    /*---------------------*/

    public Menu menu;
    BaseContract.BaseListener listener;
    Menu drawerMenu;

    public void onCreateBase() {
        listener = new BasePresenter(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        listener.loadToolbar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    @Override
    public void loadToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tbToolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void setListener(BaseContract.BaseListener listener) {
        this.listener = listener;
    }

}