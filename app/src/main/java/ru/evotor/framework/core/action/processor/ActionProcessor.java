package ru.evotor.framework.core.action.processor;


import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;

import ru.evotor.framework.core.IIntegrationManagerResponse;
import ru.evotor.framework.core.IntegrationManager;
import ru.evotor.framework.core.IntegrationResponse;

public abstract class ActionProcessor {

    public void process(String action, IIntegrationManagerResponse response, Bundle bundle) {
        process(action, bundle, new Callback(response, bundle));
    }

    public abstract void process(String action, Bundle bundle, Callback callback);

    public final class Callback {
        private IIntegrationManagerResponse response;
        private Bundle sourceData;

        private Callback(IIntegrationManagerResponse response, Bundle sourceData) {
            this.response = response;
            this.sourceData = sourceData;
        }

        public final void startActivity(Intent intent) throws RemoteException {
            if (!intent.hasExtra(IntegrationManager.KEY_INTENT_DATA)) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(IntegrationManager.KEY_INTEGRATION_RESPONSE, new IntegrationResponse(response));
                bundle.putParcelable(IntegrationManager.KEY_SOURCE_DATA, sourceData);
                intent.putExtra(IntegrationManager.KEY_INTENT_DATA, bundle);
            }

            final Bundle data = new Bundle();
            data.putParcelable(IntegrationManager.KEY_INTENT, intent);

            response.onResult(data);
        }

        public final void onResult(Bundle bundle) throws RemoteException {
            Bundle result = new Bundle();
            result.putBundle(IntegrationManager.KEY_DATA, bundle);
            response.onResult(result);
        }

        public final void onError(int errorCode, String errorMessage) throws RemoteException {
            response.onError(errorCode, errorMessage);
        }

        public final void skip() throws RemoteException {
            Bundle result = new Bundle();
            result.putBoolean(IntegrationManager.KEY_SKIP, true);

            response.onResult(result);
        }
    }
}
