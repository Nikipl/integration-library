package ru.evotor.framework.core.action.command.open_receipt_command;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ru.evotor.framework.Utils;

/**
 * Created by a.kuznetsov on 26/04/2017.
 */

public class OpenReceiptCommandResult {

    private static final String KEY_RESULT = "result";
    private static final String KEY_ERROR_CODE = "errorCode";
    private static final String KEY_RECEIPT_UUID = "receiptUuid";

    public static final int ERROR_CODE_OK = 0;
    public static final int ERROR_CODE_RECEIPT_IS_ALREADY_OPEN = -1;

    @Nullable
    public static OpenReceiptCommandResult create(@Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String resultName = bundle.getString(KEY_RESULT);

        return new OpenReceiptCommandResult(
                Utils.safeValueOf(Result.class, resultName, Result.UNKNOWN),
                bundle.getInt(KEY_ERROR_CODE, ERROR_CODE_OK),
                bundle.getString(KEY_RECEIPT_UUID)
        );
    }

    private final Result result;
    private final int errorCode;
    private final String receiptUuid;

    public OpenReceiptCommandResult(
            Result result,
            int errorCode,
            String receiptUuid
    ) {
        this.result = result;
        this.errorCode = errorCode;
        this.receiptUuid = receiptUuid;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT, result.name());
        bundle.putInt(KEY_ERROR_CODE, errorCode);
        bundle.putString(KEY_RECEIPT_UUID, receiptUuid);
        return bundle;
    }

    public Result getResult() {
        return result;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public enum Result {
        OK,
        UNKNOWN;
    }
}
