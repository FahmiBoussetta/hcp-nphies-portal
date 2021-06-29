import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './exemption-component.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ExemptionComponentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const exemptionComponentEntity = useAppSelector(state => state.exemptionComponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="exemptionComponentDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.detail.title">ExemptionComponent</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{exemptionComponentEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.type">Type</Translate>
            </span>
          </dt>
          <dd>{exemptionComponentEntity.type}</dd>
          <dt>
            <span id="start">
              <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.start">Start</Translate>
            </span>
          </dt>
          <dd>
            {exemptionComponentEntity.start ? (
              <TextFormat value={exemptionComponentEntity.start} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="end">
              <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.end">End</Translate>
            </span>
          </dt>
          <dd>
            {exemptionComponentEntity.end ? <TextFormat value={exemptionComponentEntity.end} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.exemptionComponent.costToBeneficiary">Cost To Beneficiary</Translate>
          </dt>
          <dd>{exemptionComponentEntity.costToBeneficiary ? exemptionComponentEntity.costToBeneficiary.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/exemption-component" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exemption-component/${exemptionComponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExemptionComponentDetail;
