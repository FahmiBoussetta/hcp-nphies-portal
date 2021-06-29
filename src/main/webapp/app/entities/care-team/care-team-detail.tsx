import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './care-team.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CareTeamDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const careTeamEntity = useAppSelector(state => state.careTeam.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="careTeamDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.careTeam.detail.title">CareTeam</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{careTeamEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.careTeam.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{careTeamEntity.sequence}</dd>
          <dt>
            <span id="role">
              <Translate contentKey="hcpNphiesPortalApp.careTeam.role">Role</Translate>
            </span>
          </dt>
          <dd>{careTeamEntity.role}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.careTeam.provider">Provider</Translate>
          </dt>
          <dd>{careTeamEntity.provider ? careTeamEntity.provider.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.careTeam.providerRole">Provider Role</Translate>
          </dt>
          <dd>{careTeamEntity.providerRole ? careTeamEntity.providerRole.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.careTeam.claim">Claim</Translate>
          </dt>
          <dd>{careTeamEntity.claim ? careTeamEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/care-team" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/care-team/${careTeamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CareTeamDetail;
