import React, {PropsWithChildren} from "react";
import {AuthService} from "../services/Auth";

export default function Protected({children}: PropsWithChildren<any>) {
  const isAuthenticated = AuthService.isAuthenticated();
  if (isAuthenticated) {
    if (typeof children === "function") {
      return children(AuthService.getUser())
    } else {
      return children
    }
  } else {
    return null
  }
}
