package org.ixming.androidrubick;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import android.content.Intent;

public class IntentFactory {

	static class IntentRef extends WeakReference<Intent> {

		public IntentRef(Intent r) {
			super(r);
		}

		public IntentRef(Intent r, ReferenceQueue<? super Intent> q) {
			super(r, q);
		}
		
		
		
	}
}
