import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjudicationSubDetailItem from './adjudication-sub-detail-item';
import AdjudicationSubDetailItemDetail from './adjudication-sub-detail-item-detail';
import AdjudicationSubDetailItemUpdate from './adjudication-sub-detail-item-update';
import AdjudicationSubDetailItemDeleteDialog from './adjudication-sub-detail-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationSubDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationSubDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationSubDetailItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjudicationSubDetailItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationSubDetailItemDeleteDialog} />
  </>
);

export default Routes;
