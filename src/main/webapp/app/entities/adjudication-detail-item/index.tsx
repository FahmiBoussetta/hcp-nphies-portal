import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjudicationDetailItem from './adjudication-detail-item';
import AdjudicationDetailItemDetail from './adjudication-detail-item-detail';
import AdjudicationDetailItemUpdate from './adjudication-detail-item-update';
import AdjudicationDetailItemDeleteDialog from './adjudication-detail-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationDetailItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjudicationDetailItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationDetailItemDeleteDialog} />
  </>
);

export default Routes;
