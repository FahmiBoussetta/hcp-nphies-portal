import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaymentReconciliation from './payment-reconciliation';
import PaymentReconciliationDetail from './payment-reconciliation-detail';
import PaymentReconciliationUpdate from './payment-reconciliation-update';
import PaymentReconciliationDeleteDialog from './payment-reconciliation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaymentReconciliationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaymentReconciliationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaymentReconciliationDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaymentReconciliation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaymentReconciliationDeleteDialog} />
  </>
);

export default Routes;
