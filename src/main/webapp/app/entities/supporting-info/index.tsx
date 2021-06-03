import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SupportingInfo from './supporting-info';
import SupportingInfoDetail from './supporting-info-detail';
import SupportingInfoUpdate from './supporting-info-update';
import SupportingInfoDeleteDialog from './supporting-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupportingInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupportingInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupportingInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={SupportingInfo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupportingInfoDeleteDialog} />
  </>
);

export default Routes;
