import React, {PropsWithChildren} from "react";
import {AuthService} from "../services/Auth";

export default function EndUser({children}: PropsWithChildren<any>) {
  const isEndUser = AuthService.isUser();
  if (isEndUser) {
    if (typeof children === "function") {
      return children(AuthService.getUser())
    } else {
      return children
    }
  } else {
    return null
  }
}
