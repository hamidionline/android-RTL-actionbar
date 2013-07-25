package com.markupartist.android.actionbar.example;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.Direction;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RtlHomeActivity extends Activity implements OnClickListener {

	ActionBar mActionbar;

	Button startProgress;
	Button stopProgress;
	Button addAction;
	Button removeActions;
	Button removeAction;
	Button removeShareAction;

	Action shareAction;
	Action otherAction;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_rtl);

		mActionbar = (ActionBar) findViewById(R.id.actionbar);
		mActionbar.setHomeAction(new IntentAction(this, HomeActivity.createIntent(this), R.drawable.ic_title_home_default));
		mActionbar.setDisplayHomeAsUpEnabled(true);
		mActionbar.setTitle("سلام");

		shareAction = new IntentAction(this, createShareIntent(), R.drawable.ic_title_share_default);
		mActionbar.addAction(shareAction);

		otherAction = new IntentAction(this, new Intent(this, OtherActivity.class), R.drawable.ic_title_export_default);
		mActionbar.addAction(otherAction);

		startProgress = (Button) findViewById(R.id.start_progress);
		stopProgress = (Button) findViewById(R.id.stop_progress);
		removeActions = (Button) findViewById(R.id.remove_actions);
		addAction = (Button) findViewById(R.id.add_action);
		removeAction = (Button) findViewById(R.id.remove_action);
		removeShareAction = (Button) findViewById(R.id.remove_share_action);

		startProgress.setOnClickListener(this);
		stopProgress.setOnClickListener(this);
		removeActions.setOnClickListener(this);
		addAction.setOnClickListener(this);
		removeAction.setOnClickListener(this);
		removeShareAction.setOnClickListener(this);

	}

	@Override
	public void onClick(View button) {
		if (button == startProgress) {
			mActionbar.setProgressBarVisibility(View.VISIBLE);
			return;
		}

		if (button == stopProgress) {
			mActionbar.setProgressBarVisibility(View.GONE);
			return;
		}

		if (button == addAction) {
			mActionbar.addAction(new Action() {
				@Override
				public void performAction(View view) {
					Toast.makeText(RtlHomeActivity.this, "Added action.", Toast.LENGTH_SHORT).show();
				}

				@Override
				public int getDrawable() {
					return R.drawable.ic_title_share_default;
				}
			});
			return;
		}

		if (button == removeAction) {
			int actionCount = mActionbar.getActionCount();
			if (actionCount > 0) {
				int removingItem = actionCount - 1;
				mActionbar.removeActionAt(removingItem);
				Toast.makeText(RtlHomeActivity.this, "Removed action " + removingItem, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(RtlHomeActivity.this, "No More action.", Toast.LENGTH_SHORT).show();
			}
			return;
		}

		if (button == removeActions) {
			mActionbar.removeAllActions();
			return;
		}

		if (button == removeShareAction) {
			mActionbar.removeAction(shareAction);
			return;
		}

	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, RtlHomeActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}

	private Intent createShareIntent() {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "Shared from the ActionBar widget.");
		return Intent.createChooser(intent, "Share");
	}

}