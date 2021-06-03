import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Communication from './communication';
import CommunicationDetail from './communication-detail';
import CommunicationUpdate from './communication-update';
import CommunicationDeleteDialog from './communication-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommunicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommunicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommunicationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Communication} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CommunicationDeleteDialog} />
  </>
);

export default Routes;
