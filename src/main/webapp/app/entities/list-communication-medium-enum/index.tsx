import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ListCommunicationMediumEnum from './list-communication-medium-enum';
import ListCommunicationMediumEnumDetail from './list-communication-medium-enum-detail';
import ListCommunicationMediumEnumUpdate from './list-communication-medium-enum-update';
import ListCommunicationMediumEnumDeleteDialog from './list-communication-medium-enum-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ListCommunicationMediumEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ListCommunicationMediumEnumUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ListCommunicationMediumEnumDetail} />
      <ErrorBoundaryRoute path={match.url} component={ListCommunicationMediumEnum} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ListCommunicationMediumEnumDeleteDialog} />
  </>
);

export default Routes;
