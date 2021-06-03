import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExemptionComponent from './exemption-component';
import ExemptionComponentDetail from './exemption-component-detail';
import ExemptionComponentUpdate from './exemption-component-update';
import ExemptionComponentDeleteDialog from './exemption-component-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExemptionComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExemptionComponentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExemptionComponentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExemptionComponent} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExemptionComponentDeleteDialog} />
  </>
);

export default Routes;
