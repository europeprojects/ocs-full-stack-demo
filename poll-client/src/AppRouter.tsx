import React, {Suspense, useMemo} from "react";
import {Redirect, Route, RouteProps, Switch, useLocation} from "react-router-dom";

import LoginPage from "./pages/Login";

import Home from "./pages/Home";
import {AuthService} from "./services/Auth";

export default () => {
    const isAuthenticated= AuthService.isAuthenticated();
    const isAdmin= AuthService.isAdmin();
    const isUser= AuthService.isUser();
    return (
        <Switch>
            <Route path="/login" exact component={LoginPage}/>
            <ProtectedRoute path="/home" exact component={Home}/>
            <Redirect from="/" to="/home"/>
        </Switch>
    )
}

function ProtectedRoute({component, ...props}: RouteProps) {
    let factory = () => new Promise<{ default: any }>(async (resolve) => {
        try {
            if (AuthService.isAuthenticated()) {
                resolve({default: component})
            } else {
                console.error("Token validation failed!")
                resolve({default: LoginRedirect})
            }
        } catch (_) {
            console.error("Token validation failed!")
            resolve({default: LoginRedirect})
        }
    });
    let LazyComponent = React.lazy(factory);
    return (
        <Route {...props} render={() => (
            <Suspense fallback={<div>Yükleniyor...</div>}>
                <LazyComponent/>
            </Suspense>
        )}/>
    );
}

function LoginRedirect() {
    const location = useLocation();
    return (
        <Redirect
            to={{
                pathname: "/login",
                state: {from: location}
            }}
        />
    )
}


