import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListCommunicationReasonEnum from './list-communication-reason-enum';
import ListCommunicationReasonEnumDetail from './list-communication-reason-enum-detail';
import ListCommunicationReasonEnumUpdate from './list-communication-reason-enum-update';
import ListCommunicationReasonEnumDeleteDialog from './list-communication-reason-enum-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListCommunicationReasonEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListCommunicationReasonEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListCommunicationReasonEnumDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListCommunicationReasonEnum} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListCommunicationReasonEnumDeleteDialog} />
  </>
);

export default Routes;
