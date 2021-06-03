import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Payload from './payload';
import PayloadDetail from './payload-detail';
import PayloadUpdate from './payload-update';
import PayloadDeleteDialog from './payload-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayloadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayloadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayloadDetail} />
      <ErrorBoundaryRoute path={match.url} component={Payload} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayloadDeleteDialog} />
  </>
);

export default Routes;
