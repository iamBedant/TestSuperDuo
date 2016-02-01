package barqsoft.footballscores.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by bebi2 on 2/1/2016.
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this, intent);
    }
}
