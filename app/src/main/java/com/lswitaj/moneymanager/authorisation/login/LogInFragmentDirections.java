package com.lswitaj.moneymanager.login;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.lswitaj.moneymanager.R;

public class LogInFragmentDirections {
  private LogInFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionLogInFragmentToSummaryFragment() {
    return new ActionOnlyNavDirections(R.id.action_logInFragment_to_summaryFragment);
  }

  @NonNull
  public static NavDirections actionLogInFragmentToSignUpFragment() {
    return new ActionOnlyNavDirections(R.id.action_logInFragment_to_signUpFragment);
  }
}
