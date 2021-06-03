import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ReconciliationDetailItem from './reconciliation-detail-item';
import ReconciliationDetailItemDetail from './reconciliation-detail-item-detail';
import ReconciliationDetailItemUpdate from './reconciliation-detail-item-update';
import ReconciliationDetailItemDeleteDialog from './reconciliation-detail-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ReconciliationDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ReconciliationDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ReconciliationDetailItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={ReconciliationDetailItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ReconciliationDetailItemDeleteDialog} />
  </>
);

export default Routes;
