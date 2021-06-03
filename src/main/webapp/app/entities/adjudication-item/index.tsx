import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjudicationItem from './adjudication-item';
import AdjudicationItemDetail from './adjudication-item-detail';
import AdjudicationItemUpdate from './adjudication-item-update';
import AdjudicationItemDeleteDialog from './adjudication-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjudicationItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationItemDeleteDialog} />
  </>
);

export default Routes;
