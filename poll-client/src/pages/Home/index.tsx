import React, {Suspense} from "react";
import AdminScope from "../../components/AdminScope";
import EndUserScope from "../../components/EndUserScope";

const AdminLazy = React.lazy(() => import('./AdminDashboard'));
const UserLazy = React.lazy(() => import('./UserDashboard'));


export default () => (
  <Suspense fallback={<div>Yüklüyor...</div>}>
      <AdminScope>
          <AdminLazy/>
      </AdminScope>
      <EndUserScope>
          <UserLazy/>
      </EndUserScope>
  </Suspense>
)
