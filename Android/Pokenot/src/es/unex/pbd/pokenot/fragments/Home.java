package es.unex.pbd.pokenot.fragments;

import android.app.Fragment;
import es.unex.pbd.pokenot.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Home extends Fragment{
	
	ImageView pokeball;
	RotateAnimation rotate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle SaveInstanceState) {
		super.onCreate(SaveInstanceState);
		View homeView = inflater.inflate(R.layout.home_layout, container, false);
		pokeball= (ImageView)homeView.findViewById(R.id.imagePokeball);
		//rotate= new RotateAnimation(0, 360);
		rotate= new RotateAnimation(0,360,0,500);
		rotate.setRepeatCount(60000);
		rotate.setDuration(3500);
		pokeball.setAnimation(rotate);
		return homeView;
	}

}
