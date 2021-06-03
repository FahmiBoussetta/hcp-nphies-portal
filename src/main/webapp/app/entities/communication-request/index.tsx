import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CommunicationRequest from './communication-request';
import CommunicationRequestDetail from './communication-request-detail';
import CommunicationRequestUpdate from './communication-request-update';
import CommunicationRequestDeleteDialog from './communication-request-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CommunicationRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CommunicationRequestUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CommunicationRequestDetail} />
      <ErrorBoundaryRoute path={match.url} component={CommunicationRequest} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CommunicationRequestDeleteDialog} />
  </>
);

export default Routes;
