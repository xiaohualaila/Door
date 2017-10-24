package ug.door.activity.hdmi;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import butterknife.Bind;
import butterknife.ButterKnife;
import ug.door.R;

/**
 * Created by RYX on 2016/6/23.
 */
public class MyPresentation extends Presentation {

    @Bind(R.id.present_surface)
    SurfaceView presentSurface;
    public MyPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_screen);
        ButterKnife.bind(this);
    }

    public SurfaceView getSurface() {
        return presentSurface;
    }
}
