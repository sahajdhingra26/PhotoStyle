package com.comp90018.photostyle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.comp90018.photostyle.helpers.CustomAdapter;
import com.comp90018.photostyle.helpers.Model;
import com.comp90018.photostyle.helpers.MyHelper;
import com.comp90018.photostyle.helpers.UserList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageActivity extends BaseActivity {
	private Bitmap mBitmap;
	APIInterface apiInterface;



	private ArrayList<Model> itemLabelArrayList;
	private CustomAdapter customAdapter;

	@BindView(R.id.button_nxt)
	Button btnNext;

	@BindView(R.id.button_save)
	Button btnSave;

	@BindView(R.id.image_view)
	ImageView mImageView;

	@BindView(R.id.lvImageLabel)
	ListView lvImageLabel;

	@BindView(R.id.lvSeason)
	ListView lvSeason;

	@BindView(R.id.lvType)
	ListView lvType;

	@BindView(R.id.lvCategory)
	ListView lvCategory;

	@BindView(R.id.textInstructions)
	TextView textInstructions;

	int step=0;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud);
		ButterKnife.bind(this);
		apiInterface = APIClient.getClient2().create(APIInterface.class);
		btnNext.setVisibility(View.INVISIBLE);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Clicked","Step is "+step);
				if(step==0){
					step=1;
					populateSeason();
					lvImageLabel.setVisibility(View.GONE);
					lvSeason.setVisibility(View.VISIBLE);
					lvType.setVisibility(View.GONE);
					lvCategory.setVisibility(View.GONE);

				}
				else if(step==1){
					step=2;
					populateType();
					lvImageLabel.setVisibility(View.GONE);
					lvSeason.setVisibility(View.GONE);
					lvType.setVisibility(View.VISIBLE);
					lvCategory.setVisibility(View.GONE);

				}
				else if(step==2){
					step=3;
					populateCategory();
					lvImageLabel.setVisibility(View.GONE);
					lvSeason.setVisibility(View.GONE);
					lvType.setVisibility(View.GONE);
					lvCategory.setVisibility(View.VISIBLE);

				}
				else if(step==3){
					step=4;
					lvImageLabel.setVisibility(View.GONE);
					lvSeason.setVisibility(View.GONE);
					lvType.setVisibility(View.GONE);
					lvCategory.setVisibility(View.GONE);
					textInstructions.setVisibility(View.GONE);
					btnSave.setVisibility(View.VISIBLE);
					btnNext.setVisibility(View.GONE);

				}

			}
		});


		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("Selected", Prefs.getString("image_label",""));
				Log.d("Selected", Prefs.getString("type",""));
				Log.d("Selected", Prefs.getString("category",""));
				Log.d("Selected", Prefs.getString("season",""));
				String email = Prefs.getString("email","");
				String src = Prefs.getString("src","");
				String season = Prefs.getString("season","");
				String type = Prefs.getString("type","");
				String category = Prefs.getString("category","");
				String image_label = Prefs.getString("image_label","");



				try {
					Call<UserList> call2 = apiInterface.saveWardrobe(email, src,season,type,category,image_label);
					call2.enqueue(new Callback<UserList>() {
						@Override
						public void onResponse(Call<UserList> call2, Response<UserList> response) {

							UserList userList = response.body();
							if(userList.getSuccess()==1){
								Toast.makeText(getBaseContext(), R.string.imageAdded, Toast.LENGTH_LONG).show();
								finish();
							}
							else if(userList.getSuccess()==0){


							}
						}

						@Override
						public void onFailure(Call<UserList> call, Throwable t) {
							call.cancel();


						}
					});
				}
				catch (Exception e){
					e.printStackTrace();
				}





			}

		});





	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			switch (requestCode) {
				case RC_STORAGE_PERMS1:
				case RC_STORAGE_PERMS2:
					checkStoragePermission(requestCode);
					break;
				case RC_SELECT_PICTURE:
					Uri dataUri = data.getData();
					String path = MyHelper.getPath(this, dataUri);
					if (path == null) {
						mBitmap = MyHelper.resizeImage(imageFile, this, dataUri, mImageView);
					} else {
						mBitmap = MyHelper.resizeImage(imageFile, path, mImageView);

					}
					if (mBitmap != null) {

						mImageView.setImageBitmap(mBitmap);
						getLabel();
					}
					break;
				case RC_TAKE_PICTURE:
					mBitmap = MyHelper.resizeImage(imageFile, imageFile.getPath(), mImageView);
					if (mBitmap != null) {

						mImageView.setImageBitmap(mBitmap);
						getLabel();

					}
					break;
			}
		}
	}

	private void getLabel(){

		if (mBitmap != null) {
			MyHelper.showDialog(this);
			FirebaseVisionCloudImageLabelerOptions options = new FirebaseVisionCloudImageLabelerOptions.Builder().setConfidenceThreshold(0.7f).build();
			FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(mBitmap);
			FirebaseVisionImageLabeler detector = FirebaseVision.getInstance().getCloudImageLabeler(options);
			detector.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
				@Override
				public void onSuccess(List<FirebaseVisionImageLabel> labels) {
					btnNext.setVisibility(View.VISIBLE);
					textInstructions.setText(R.string.selectImageLabel);
					lvImageLabel.setVisibility(View.VISIBLE);
					MyHelper.dismissDialog();
					extractLabel(labels);
					SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
					String imageName = s.format(new Date());
					MyHelper.saveImage(mBitmap,imageName);

				}
			}).addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception e) {
					MyHelper.dismissDialog();
					btnNext.setVisibility(View.GONE);
					lvImageLabel.setVisibility(View.GONE);
					textInstructions.setText(R.string.error);


				}
			});
		}
	}

	private void extractLabel(List<FirebaseVisionImageLabel> labels) {
		ArrayList<Model> list = new ArrayList<>();

		for (FirebaseVisionImageLabel label : labels) {

			Model model = new Model();
			model.setSelected(false);
			model.setItemLabel(label.getText());
			list.add(model);
			customAdapter = new CustomAdapter(this,list);
			customAdapter.setTypeBox("image_label");
			lvImageLabel.setAdapter(customAdapter);

		}

	}

	private void populateSeason(){
		textInstructions.setText(R.string.selectSeason);
		ArrayList<Model> list = new ArrayList<>();
		String[] seasons = {
				"Summer(Sunny)","Winter(Cold)","Rainy(Monsoon)"
		};

		for (String season : seasons){
			Model model = new Model();
			model.setSelected(false);
			model.setItemLabel(season);
			list.add(model);
			customAdapter = new CustomAdapter(this,list);
			customAdapter.setTypeBox("season");
			lvSeason.setAdapter(customAdapter);
		}




	}

	private void populateType(){
		textInstructions.setText(R.string.selectType);
		ArrayList<Model> list = new ArrayList<>();
		String[] types = {
				"Casuals","Business formals","Party Wear"
		};

		for (String type : types){
			Model model = new Model();
			model.setSelected(false);
			model.setItemLabel(type);
			list.add(model);
			customAdapter = new CustomAdapter(this,list);
			customAdapter.setTypeBox("type");
			lvType.setAdapter(customAdapter);
		}

	}

	private void populateCategory(){
		textInstructions.setText(R.string.selectCategory);
		ArrayList<Model> list = new ArrayList<>();
		String[] types = {
				"Uppers","Lowers","Footwear","Accessories"
		};

		for (String type : types){
			Model model = new Model();
			model.setSelected(false);
			model.setItemLabel(type);
			list.add(model);
			customAdapter = new CustomAdapter(this,list);
			customAdapter.setTypeBox("category");
			lvCategory.setAdapter(customAdapter);
		}

	}



}
