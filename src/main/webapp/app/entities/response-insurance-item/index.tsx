import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ResponseInsuranceItem from './response-insurance-item';
import ResponseInsuranceItemDetail from './response-insurance-item-detail';
import ResponseInsuranceItemUpdate from './response-insurance-item-update';
import ResponseInsuranceItemDeleteDialog from './response-insurance-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResponseInsuranceItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResponseInsuranceItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResponseInsuranceItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={ResponseInsuranceItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResponseInsuranceItemDeleteDialog} />
  </>
);

export default Routes;
