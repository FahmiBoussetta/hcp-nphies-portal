import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SubDetailItem from './sub-detail-item';
import SubDetailItemDetail from './sub-detail-item-detail';
import SubDetailItemUpdate from './sub-detail-item-update';
import SubDetailItemDeleteDialog from './sub-detail-item-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubDetailItemUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubDetailItemDetail} />
      <ErrorBoundaryRoute path={match.url} component={SubDetailItem} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SubDetailItemDeleteDialog} />
  </>
);

export default Routes;
