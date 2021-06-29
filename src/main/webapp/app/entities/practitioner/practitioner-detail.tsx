import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './practitioner.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PractitionerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const practitionerEntity = useAppSelector(state => state.practitioner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="practitionerDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.practitioner.detail.title">Practitioner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{practitionerEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.practitioner.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{practitionerEntity.guid}</dd>
          <dt>
            <span id="forceId">
              <Translate contentKey="hcpNphiesPortalApp.practitioner.forceId">Force Id</Translate>
            </span>
          </dt>
          <dd>{practitionerEntity.forceId}</dd>
          <dt>
            <span id="practitionerLicense">
              <Translate contentKey="hcpNphiesPortalApp.practitioner.practitionerLicense">Practitioner License</Translate>
            </span>
          </dt>
          <dd>{practitionerEntity.practitionerLicense}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="hcpNphiesPortalApp.practitioner.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{practitionerEntity.gender}</dd>
        </dl>
        <Button tag={Link} to="/practitioner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/practitioner/${practitionerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PractitionerDetail;
