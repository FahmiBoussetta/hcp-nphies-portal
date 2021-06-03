import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResponseInsurance from './response-insurance';
import ResponseInsuranceDetail from './response-insurance-detail';
import ResponseInsuranceUpdate from './response-insurance-update';
import ResponseInsuranceDeleteDialog from './response-insurance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResponseInsuranceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResponseInsuranceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResponseInsuranceDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResponseInsurance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResponseInsuranceDeleteDialog} />
  </>
);

export default Routes;
