package prometheus.zero.base;

/**
 * Created by ishwor on 31/03/16.
 */
public interface BaseContract {

    interface View {

        void loadToolbar();

        void setListener(BaseListener baseListener);
    }

    interface BaseListener {

        void loadToolbar();
    }
}
