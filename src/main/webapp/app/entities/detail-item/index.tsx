import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DetailItem from './detail-item';
import DetailItemDetail from './detail-item-detail';
import DetailItemUpdate from './detail-item-update';
import DetailItemDeleteDialog from './detail-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DetailItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={DetailItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DetailItemDeleteDialog} />
  </>
);

export default Routes;
