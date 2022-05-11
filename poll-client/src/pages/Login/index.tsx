import React, {Suspense} from "react";

const LazyComponent = React.lazy(() => import('./Login'));

export default () => (
  <Suspense fallback={<div>Yükleniyor...</div>}>
    <LazyComponent/>
  </Suspense>
)
