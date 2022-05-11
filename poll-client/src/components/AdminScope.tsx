import React, {PropsWithChildren} from "react";
import {AuthService} from "../services/Auth";

export default function AdminScope({children}: PropsWithChildren<any>) {
  const isAdmin = AuthService.isAdmin();
  if (isAdmin) {
    if (typeof children === "function") {
      return children(AuthService.getUser())
    } else {
      return children
    }
  } else {
    return null
  }
}
