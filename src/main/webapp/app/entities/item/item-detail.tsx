import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const itemEntity = useAppSelector(state => state.item.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.item.detail.title">Item</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.item.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{itemEntity.sequence}</dd>
          <dt>
            <span id="isPackage">
              <Translate contentKey="hcpNphiesPortalApp.item.isPackage">Is Package</Translate>
            </span>
          </dt>
          <dd>{itemEntity.isPackage ? 'true' : 'false'}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="hcpNphiesPortalApp.item.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{itemEntity.tax}</dd>
          <dt>
            <span id="payerShare">
              <Translate contentKey="hcpNphiesPortalApp.item.payerShare">Payer Share</Translate>
            </span>
          </dt>
          <dd>{itemEntity.payerShare}</dd>
          <dt>
            <span id="patientShare">
              <Translate contentKey="hcpNphiesPortalApp.item.patientShare">Patient Share</Translate>
            </span>
          </dt>
          <dd>{itemEntity.patientShare}</dd>
          <dt>
            <span id="careTeamSequence">
              <Translate contentKey="hcpNphiesPortalApp.item.careTeamSequence">Care Team Sequence</Translate>
            </span>
          </dt>
          <dd>{itemEntity.careTeamSequence}</dd>
          <dt>
            <span id="transportationSRCA">
              <Translate contentKey="hcpNphiesPortalApp.item.transportationSRCA">Transportation SRCA</Translate>
            </span>
          </dt>
          <dd>{itemEntity.transportationSRCA}</dd>
          <dt>
            <span id="imaging">
              <Translate contentKey="hcpNphiesPortalApp.item.imaging">Imaging</Translate>
            </span>
          </dt>
          <dd>{itemEntity.imaging}</dd>
          <dt>
            <span id="laboratory">
              <Translate contentKey="hcpNphiesPortalApp.item.laboratory">Laboratory</Translate>
            </span>
          </dt>
          <dd>{itemEntity.laboratory}</dd>
          <dt>
            <span id="medicalDevice">
              <Translate contentKey="hcpNphiesPortalApp.item.medicalDevice">Medical Device</Translate>
            </span>
          </dt>
          <dd>{itemEntity.medicalDevice}</dd>
          <dt>
            <span id="oralHealthIP">
              <Translate contentKey="hcpNphiesPortalApp.item.oralHealthIP">Oral Health IP</Translate>
            </span>
          </dt>
          <dd>{itemEntity.oralHealthIP}</dd>
          <dt>
            <span id="oralHealthOP">
              <Translate contentKey="hcpNphiesPortalApp.item.oralHealthOP">Oral Health OP</Translate>
            </span>
          </dt>
          <dd>{itemEntity.oralHealthOP}</dd>
          <dt>
            <span id="procedure">
              <Translate contentKey="hcpNphiesPortalApp.item.procedure">Procedure</Translate>
            </span>
          </dt>
          <dd>{itemEntity.procedure}</dd>
          <dt>
            <span id="services">
              <Translate contentKey="hcpNphiesPortalApp.item.services">Services</Translate>
            </span>
          </dt>
          <dd>{itemEntity.services}</dd>
          <dt>
            <span id="medicationCode">
              <Translate contentKey="hcpNphiesPortalApp.item.medicationCode">Medication Code</Translate>
            </span>
          </dt>
          <dd>{itemEntity.medicationCode}</dd>
          <dt>
            <span id="servicedDate">
              <Translate contentKey="hcpNphiesPortalApp.item.servicedDate">Serviced Date</Translate>
            </span>
          </dt>
          <dd>{itemEntity.servicedDate ? <TextFormat value={itemEntity.servicedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="servicedDateStart">
              <Translate contentKey="hcpNphiesPortalApp.item.servicedDateStart">Serviced Date Start</Translate>
            </span>
          </dt>
          <dd>
            {itemEntity.servicedDateStart ? <TextFormat value={itemEntity.servicedDateStart} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="servicedDateEnd">
              <Translate contentKey="hcpNphiesPortalApp.item.servicedDateEnd">Serviced Date End</Translate>
            </span>
          </dt>
          <dd>
            {itemEntity.servicedDateEnd ? <TextFormat value={itemEntity.servicedDateEnd} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="hcpNphiesPortalApp.item.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{itemEntity.quantity}</dd>
          <dt>
            <span id="unitPrice">
              <Translate contentKey="hcpNphiesPortalApp.item.unitPrice">Unit Price</Translate>
            </span>
          </dt>
          <dd>{itemEntity.unitPrice}</dd>
          <dt>
            <span id="factor">
              <Translate contentKey="hcpNphiesPortalApp.item.factor">Factor</Translate>
            </span>
          </dt>
          <dd>{itemEntity.factor}</dd>
          <dt>
            <span id="bodySite">
              <Translate contentKey="hcpNphiesPortalApp.item.bodySite">Body Site</Translate>
            </span>
          </dt>
          <dd>{itemEntity.bodySite}</dd>
          <dt>
            <span id="subSite">
              <Translate contentKey="hcpNphiesPortalApp.item.subSite">Sub Site</Translate>
            </span>
          </dt>
          <dd>{itemEntity.subSite}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.item.claim">Claim</Translate>
          </dt>
          <dd>{itemEntity.claim ? itemEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item/${itemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemDetail;
