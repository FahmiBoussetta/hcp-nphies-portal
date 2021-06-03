import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Practitioner from './practitioner';
import PractitionerDetail from './practitioner-detail';
import PractitionerUpdate from './practitioner-update';
import PractitionerDeleteDialog from './practitioner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PractitionerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PractitionerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PractitionerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Practitioner} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PractitionerDeleteDialog} />
  </>
);

export default Routes;
